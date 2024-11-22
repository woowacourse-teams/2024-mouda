import { postBet } from '@_apis/posts';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import QUERY_KEYS from '@_constants/queryKeys';
import { useMutation, useQueryClient } from '@tanstack/react-query';

export default function useAddBet(onSuccess: (betId: number) => void) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: postBet,
    onSuccess: (betId: number) => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.darakbang, getLastDarakbangId(), QUERY_KEYS.bets],
      });

      onSuccess(betId);
    },
  });
}
