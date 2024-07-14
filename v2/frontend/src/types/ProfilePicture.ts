export interface ProfilePicture {
    originalImage: OriginalImage
    thumbnail: Thumbnail
}

export interface OriginalImage {
    preSignedUrl: string
    expiration: string
}

export interface Thumbnail {
    preSignedUrl: string
    expiration: string
}

export const profilePictureDefault: ProfilePicture = {
    originalImage: {
        preSignedUrl: "/avatar.png",
        expiration: "2099-07-14T13:07:31.933089Z"
    },
    thumbnail: {
        preSignedUrl: "/avatar.png",
        expiration: "2099-07-14T13:07:31.933089Z"
    }
};