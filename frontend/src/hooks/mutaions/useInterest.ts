import { postInterest } from '@_apis/posts';
import QUERY_KEYS from '@_constants/queryKeys';
import { Please } from '@_types/index';
import { useMutation, useQueryClient } from '@tanstack/react-query';

export default function useInterest(please: Please) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: () => postInterest(please.pleaseId, !please.isInterested),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.pleases],
      });
    },
  });
}
