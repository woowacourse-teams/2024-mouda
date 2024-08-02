import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';
import { postChangeZzim } from '@_apis/posts';

export default function useChangeZzim() {
  const queryClient = useQueryClient();

  return useMutation<void, Error, number>({
    mutationFn: postChangeZzim,
    onSuccess: (_, moimId) => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.ZzimMine, moimId],
      });
    },
  });
}
