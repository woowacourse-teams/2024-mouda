import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';
import { postWriteComment } from '@_apis/posts';

export default function useWriteComment() {
  const queryClient = useQueryClient();

  return useMutation<void, Error, number>({
    mutationFn: postWriteComment,
    onSuccess: (_, moimId) => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.comment, moimId],
      });
    },
  });
}
