import { postConfirmDatetime } from '@_apis/posts';
import { useMutation } from '@tanstack/react-query';

export default function useConfirmDateTime() {
  return useMutation({
    mutationFn: async ({
      moimId,
      date,
      time,
    }: {
      moimId: number;
      date: string;
      time: string;
    }) => postConfirmDatetime(moimId, date, time),
  });
}
