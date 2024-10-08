import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { postConfirmPlace } from '@_apis/posts';

export default function useConfirmPlace() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: ({ moimId, place }: { moimId: number; place: string }) =>
      postConfirmPlace(moimId, place),
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
