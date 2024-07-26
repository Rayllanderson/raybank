export interface ProfilePicture {
    originalImage: OriginalImage
    thumbnail?: Thumbnail
}

export interface OriginalImage {
    preSignedUrl: string
    expiration: string
}

export interface Thumbnail {
    preSignedUrl: string
    expiration: string
}