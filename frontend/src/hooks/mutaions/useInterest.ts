import { useMutation, useQueryClient } from '@tanstack/react-query';

import { Please } from '@_types/index';
import QUERY_KEYS from '@_constants/queryKeys';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { postInterest } from '@_apis/posts';

export default function useInterest(please: Please) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: () => postInterest(please.pleaseId, !please.isInterested),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [
          QUERY_KEYS.darakbang,
          getLastDarakbangId(),
          QUERY_KEYS.pleases,
        ],
      });
    },
  });
}
