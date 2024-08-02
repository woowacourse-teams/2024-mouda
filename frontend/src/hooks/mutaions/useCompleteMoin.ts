import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';

import { fetchCompleteMoin } from '@_apis/fetchs';

export default function useCompleteMoin() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: fetchCompleteMoin,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.moim],
      });
    },
  });
}
