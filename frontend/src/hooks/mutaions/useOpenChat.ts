import { patchOpenChat } from '@_apis/patches';
import { useMutation } from '@tanstack/react-query';

export default function useOpenChat(onSuccess: () => void) {
  return useMutation({
    mutationFn: patchOpenChat,
    onSuccess: onSuccess,
  });
}
