import { postConfirmPlace } from '@_apis/posts';
import { useMutation } from '@tanstack/react-query';

export default function useConfirmPlace() {
  return useMutation({
    mutationFn: async ({ moimId, place }: { moimId: number; place: string }) =>
      postConfirmPlace(moimId, place),
  });
}
