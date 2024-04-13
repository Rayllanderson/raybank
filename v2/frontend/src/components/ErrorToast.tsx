import toast from 'react-hot-toast';

export default function ErrorToast({message}: {message: string}) {
  return (
    toast.error(message)
  )
}
