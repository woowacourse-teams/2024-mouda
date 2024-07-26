import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';
import { postJoinMoim } from '@_apis/posts';

export default function useJoinMoim(onSuccess: () => void) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ moimId, nickname }: { moimId: number; nickname: string }) =>
      postJoinMoim(moimId, nickname),
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.moim],
      });
      onSuccess();
    },
  });
}
