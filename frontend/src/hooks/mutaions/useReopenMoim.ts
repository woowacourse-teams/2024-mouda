import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';

import { fetchReopenMoin } from '@_apis/fetchs';

export default function useReopenMoim() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: fetchReopenMoin,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.moim],
      });
    },
  });
}
