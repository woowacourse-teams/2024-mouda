import { postChat } from '@_apis/posts';
import { useMutation } from '@tanstack/react-query';

export default function useSendMessage(chatRoomId: number) {
  return useMutation({
    mutationFn: (message: string) => postChat(chatRoomId, message),
  });
}
