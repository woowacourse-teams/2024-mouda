import { postNotificationToken } from '@_apis/posts';
import { useMutation } from '@tanstack/react-query';

export default function useServeToken() {
  return useMutation({
    mutationFn: postNotificationToken,
  });
}
