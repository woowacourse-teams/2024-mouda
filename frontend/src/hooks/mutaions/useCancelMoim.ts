import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';

import { fetchCancelMoin } from '@_apis/fetchs';

export default function useCancelMoim() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: fetchCancelMoin,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.moim],
      });
    },
  });
}
