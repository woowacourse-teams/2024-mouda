import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';
import { postMoim } from '@_apis/posts';

export default function useAddMoim(onSuccess: () => void) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: postMoim,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.moims] });
      onSuccess();
    },
  });
}
