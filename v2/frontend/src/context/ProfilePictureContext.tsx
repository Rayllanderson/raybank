// context/ProfilePictureContext.tsx
import React, { createContext, useContext, useState, useEffect, ReactNode } from 'react';
import { deleteProfilePicture, getProfilePicture } from '@/services/ProfilePictureService';
import { ProfilePicture } from '@/types/ProfilePicture';
import { useSession } from 'next-auth/react';

interface ProfilePictureContextProps {
    profilePicture: ProfilePicture | null;
    updatePicture: (profilePicture: ProfilePicture | null) => void;
    isThumbCreating: boolean;
    deletePicture: () => void
}

const WITHOUT_PICTURE = 'WITHOUT_PICTURE'

const ProfilePictureContext = createContext<ProfilePictureContextProps | undefined>(undefined);

export const ProfilePictureProvider = ({ children }: { children: ReactNode }) => {
    const [profilePicture, setProfilePicture] = useState<ProfilePicture | null>(null);
    const { data: session, status } = useSession();
    const [isThumbCreating, setIsThumbCreating] = useState<boolean>(false);

    const fetchProfilePicture = async () => {

        const response = await getProfilePicture(session?.token!)

        if (response === WITHOUT_PICTURE) {
            localStorage.setItem('profilePicture', WITHOUT_PICTURE);
            return
        }

        setProfilePicture(response as ProfilePicture);

        localStorage.setItem('profilePicture', JSON.stringify(response));
    }

    useEffect(() => {
        if (status === 'loading') {
            return
        }
        loadImage()
    }, [status])


    function loadImage() {
        const storedProfilePicture = localStorage.getItem('profilePicture');

        if (storedProfilePicture === WITHOUT_PICTURE) {
            return
        }

        if (storedProfilePicture === null || storedProfilePicture === undefined) {
            fetchProfilePicture()
            return
        }

        const profilePictureData: ProfilePicture = JSON.parse(storedProfilePicture);

        if (isProfilePictureExpired(profilePictureData?.originalImage?.expiration)) {
            fetchProfilePicture()
            return;
        }

        if (profilePictureData.thumbnail === undefined || profilePictureData.thumbnail === null) {
            fetchProfilePicture()
            return;
        }

        setProfilePicture(profilePictureData);
    }

    function updatePicture(profilePicture: ProfilePicture | null) {
        setProfilePicture(profilePicture);
        
        localStorage.setItem('profilePicture', JSON.stringify(profilePicture));

        loadThumbnailAsync()
    }

    const deletePicture = async () => {
        await deleteProfilePicture(session?.token!)

        setProfilePicture(null)

        localStorage.setItem('profilePicture', WITHOUT_PICTURE);

        setIsThumbCreating(false)
    }


    const loadThumbnailAsync = () => {
        setIsThumbCreating(true)
        let attempts = 0;
        const maxAttempts = 10;

        const intervalId = setInterval(async () => {
            attempts++;
            const storedProfilePicture = localStorage.getItem('profilePicture');

            if (storedProfilePicture === null || storedProfilePicture === undefined) {
                clearInterval(intervalId)
                return
            }

            const profilePictureData: ProfilePicture = JSON.parse(storedProfilePicture!);

            if (profilePictureData === null || profilePictureData === undefined) {
                clearInterval(intervalId)
                return
            }

            fetchProfilePicture()

            if (profilePictureData!.thumbnail?.preSignedUrl) {
                clearInterval(intervalId);
                setIsThumbCreating(false)
            }

            if (attempts >= maxAttempts) {
                clearInterval(intervalId);
                setIsThumbCreating(false);
                console.log('Limite de tentativas atingido. Miniatura não encontrada.');
            }
        }, 1000);
    }

    return (
        <ProfilePictureContext.Provider value={{ deletePicture, isThumbCreating, profilePicture, updatePicture }}>
            {children}
        </ProfilePictureContext.Provider>
    );
}

export const useProfilePicture = (): ProfilePictureContextProps => {
    const context = useContext(ProfilePictureContext);
    if (!context) {
        throw new Error('useProfilePicture must be used within a ProfilePictureProvider');
    }
    return context;
};

const isProfilePictureExpired = (expiration?: string): boolean => {
    if (!expiration) return true
    const expirationDate = new Date(expiration!);
    const now = new Date();
    return now >= expirationDate;
};


