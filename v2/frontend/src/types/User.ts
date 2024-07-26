import { ProfilePicture } from "./ProfilePicture";

export interface User {
    id: string;
    name: string;
    email: string;
    profilePicture?: ProfilePicture | null
}