import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { postJoinMoim } from '@_apis/posts';

export default function useJoinMoim(onSuccess: () => void) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: postJoinMoim,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.darakbang, getLastDarakbangId(), QUERY_KEYS.moim],
      });
      onSuccess();
    },
  });
}
