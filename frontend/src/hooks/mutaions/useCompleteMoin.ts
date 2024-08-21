import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { patchCompleteMoim } from '@_apis/patches';

export default function useCompleteMoin() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: patchCompleteMoim,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.darakbang, getLastDarakbangId(), QUERY_KEYS.moim],
      });
    },
  });
}
