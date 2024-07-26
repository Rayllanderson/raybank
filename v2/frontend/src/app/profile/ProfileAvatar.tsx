'use client'

import LightButton from "@/components/Buttons/LightButton";
import PrimaryButton from "@/components/Buttons/PrimaryButton";
import { handlerApiError } from "@/services/HandlerApiError";
import { Avatar } from "flowbite-react";
import { useSession } from "next-auth/react";
import React, { ChangeEvent, useState } from 'react';
import toast from "react-hot-toast";
import { FaTrash, FaUpload } from "react-icons/fa6";
import { Modal } from "flowbite-react";
import { HiOutlineExclamationCircle } from "react-icons/hi";
import Button from "@/components/Buttons/Button";
import { useProfilePicture } from "@/context/ProfilePictureContext";
import { deleteProfilePicture, uploadProfilePicture } from "@/services/ProfilePictureService";
import AvatarLoading from "@/components/AvatarLoading";

const ProfileAvatar = () => {
    const { profilePicture, updatePicture, deletePicture } = useProfilePicture();
    const { data: session, status } = useSession();
    const [selectedFile, setSelectedFile] = useState<File | null>(null);
    const [preview, setPreview] = useState<string | null>(null);
    const [isLoading, setIsLoading] = useState<boolean>(false);
    const [openModal, setOpenModal] = useState(false);


    const handleFileChange = (event: ChangeEvent<HTMLInputElement>) => {
        const file = event.target.files ? event.target.files[0] : null;
        if (file) {
            setSelectedFile(file);
            setPreview(URL.createObjectURL(file));
        }
    };

    const handleCancelUpload = async () => {
        setSelectedFile(null);
        setPreview(null);
    };

    const handleConfirmUpload = async () => {
        if (!selectedFile) return;
        setIsLoading(true);

        const data = new FormData();
        data.append('file', selectedFile);
        try {
            const result = await uploadProfilePicture(data, session?.token!);
            setSelectedFile(null);
            updatePicture({
                originalImage: result
            });
            setPreview(null)
            toast.success('Imagem Atualizada')
        } catch (error) {
            handlerApiError(error);
        } finally {
            setIsLoading(false);
        }
    };

    const handleDelete = async () => {
        setIsLoading(true);

        try {
            await deletePicture()
            toast.success('Imagem removida')
        } catch (error) {
            handlerApiError(error);
        } finally {
            setIsLoading(false);
            setOpenModal(false)
        }
    };

    return (
        status === 'loading' ?
            <AvatarLoading size='xl' bordered />
            : (
                <div className="relative inline-block">
                    <ConfirmModal openModal={openModal} setOpenModal={setOpenModal} loading={isLoading} action={handleDelete} />
                    <div className="relative">
                        <Avatar img={preview || profilePicture?.originalImage.preSignedUrl || undefined} size="xl" rounded bordered color="primary" />
                        <div className="absolute inset-0 flex items-center justify-center bg-black bg-opacity-50 rounded-full z-10 opacity-0 hover:opacity-100 transition-opacity duration-200 ease-in-out">
                            <label className="text-white px-4 py-2 rounded-full cursor-pointer">
                                <FaUpload className="h-6 w-6" />
                                <input type="file" accept="image/*" className="hidden" onChange={handleFileChange} placeholder="upload image" />
                            </label>
                            {profilePicture && <button title={'Remover imagem'} className="text-white px-4 py-2  rounded ml-2" onClick={() => setOpenModal(true)}> <FaTrash className="h-6 w-6" /></button>
                            }
                        </div>
                    </div>
                    {preview && (
                        <div className="flex space-x-2 mt-4">
                            <PrimaryButton loading={isLoading} disabled={isLoading} onClick={handleConfirmUpload}> Confirmar </PrimaryButton>
                            <LightButton onClick={handleCancelUpload}> Cancelar </LightButton>
                        </div>
                    )}
                </div>
            )

    );
};



function ConfirmModal({ openModal, setOpenModal, action, loading }: { loading: any, openModal: any, setOpenModal: any, action: any }) {

    return (
        <>
            <Modal show={openModal} size="md" onClose={() => setOpenModal(false)} popup className="">
                <Modal.Header />
                <Modal.Body>
                    <div className="text-center">
                        <HiOutlineExclamationCircle className="mx-auto mb-4 h-14 w-14 text-gray-400 dark:text-gray-200" />
                        <h3 className="mb-5 text-lg font-normal text-gray-500 dark:text-gray-400">
                            Tem certeza que deseja remover sua foto?
                        </h3>
                        <div className="flex justify-center gap-4">
                            <Button color="failure" onClick={action} loading={loading} disabled={loading}>
                                Sim, remover
                            </Button>
                            <Button color="light" onClick={() => setOpenModal(false)}>
                                Não, manter
                            </Button>
                        </div>
                    </div>
                </Modal.Body>
            </Modal>
        </>
    );
}


export default ProfileAvatar;
