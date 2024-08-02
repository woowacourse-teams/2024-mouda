import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';

import { fetchModifyMoin } from '@_apis/fetchs';

export default function useModifyMoim() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: fetchModifyMoin,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.moim],
      });
    },
  });
}
