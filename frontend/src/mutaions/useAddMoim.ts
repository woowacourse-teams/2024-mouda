import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '../queryKeys';
import { postMoim } from '../apis/posts';

export default function useAddToCart() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: postMoim,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.moims] });
    },
  });
}
