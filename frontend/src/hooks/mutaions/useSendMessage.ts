import { postChat } from '@_apis/posts';
import { useMutation } from '@tanstack/react-query';

export default function useSendMessage(moimId: number) {
  return useMutation({
    mutationFn: (message: string) => postChat(moimId, message),
  });
}
