import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { postConfirmDatetime } from '@_apis/posts';

export default function useConfirmDateTime() {
  const queryClient = useQueryClient();
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
    onSuccess: (moimId: number | string) => {
      queryClient.invalidateQueries({
        queryKey: [
          QUERY_KEYS.darakbang,
          getLastDarakbangId(),
          QUERY_KEYS.moim,
          moimId,
        ],
      });
    },
  });
}
