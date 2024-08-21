import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { patchCancelMoim } from '@_apis/patches';

export default function useCancelMoim() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: patchCancelMoim,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.darakbang, getLastDarakbangId(), QUERY_KEYS.moim],
      });
    },
  });
}
