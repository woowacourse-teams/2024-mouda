import { postBetResult } from '@_apis/posts';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import QUERY_KEYS from '@_constants/queryKeys';
import { useMutation, useQueryClient } from '@tanstack/react-query';

export default function useCompleteBet() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: postBetResult,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.darakbang, getLastDarakbangId(), QUERY_KEYS.bet],
      });
    },
  });
}
