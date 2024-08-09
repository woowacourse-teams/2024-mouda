import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';

import { deleteCancelChamyo } from '@_apis/deletes';

export default function useCancelChamyo() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: deleteCancelChamyo,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.chamyoMine],
      });
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.chamyoAll],
      });
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.moim],
      });
    },
  });
}
