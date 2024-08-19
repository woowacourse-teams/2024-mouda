import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { postChangeZzim } from '@_apis/posts';

export default function useChangeZzim() {
  const queryClient = useQueryClient();

  return useMutation<void, Error, number>({
    mutationFn: postChangeZzim,
    onSuccess: (_, moimId) => {
      queryClient.invalidateQueries({
        queryKey: [
          QUERY_KEYS.darakbang,
          getLastDarakbangId(),
          QUERY_KEYS.ZzimMine,
          moimId,
        ],
      });
      queryClient.invalidateQueries({
        queryKey: [
          QUERY_KEYS.darakbang,
          getLastDarakbangId(),
          QUERY_KEYS.moims,
        ],
      });
      queryClient.invalidateQueries({
        queryKey: [
          QUERY_KEYS.darakbang,
          getLastDarakbangId(),
          QUERY_KEYS.myMoims,
        ],
      });
      queryClient.invalidateQueries({
        queryKey: [
          QUERY_KEYS.darakbang,
          getLastDarakbangId(),
          QUERY_KEYS.myZzimMoims,
        ],
      });
    },
  });
}
