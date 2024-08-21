import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { patchReopenMoim } from '@_apis/patches';

export default function useReopenMoim() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: patchReopenMoim,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.darakbang, getLastDarakbangId(), QUERY_KEYS.moim],
      });
    },
  });
}
