import { postJoinBet } from '@_apis/posts';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import QUERY_KEYS from '@_constants/queryKeys';
import { useMutation, useQueryClient } from '@tanstack/react-query';

export default function useJoinBet() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: postJoinBet,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.darakbang, getLastDarakbangId(), QUERY_KEYS.bet],
      });
    },
  });
}
