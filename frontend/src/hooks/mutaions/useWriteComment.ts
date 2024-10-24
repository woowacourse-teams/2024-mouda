import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { postWriteComment } from '@_apis/posts';

interface WriteCommentParams {
  moimId: number;
  selectedComment: number;
  message: string;
}

export default function useWriteComment() {
  const queryClient = useQueryClient();

  return useMutation<void, Error, WriteCommentParams>({
    mutationFn: ({ moimId, selectedComment, message }) =>
      postWriteComment(moimId, selectedComment, message),
    onSuccess: (_, { moimId }) => {
      queryClient.invalidateQueries({
        queryKey: [
          QUERY_KEYS.darakbang,
          getLastDarakbangId(),
          QUERY_KEYS.moim,
          moimId,
        ],
      });
    },
  });
}
