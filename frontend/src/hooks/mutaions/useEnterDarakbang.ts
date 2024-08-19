import { useMutation, useQueryClient } from '@tanstack/react-query';

import { ApiError } from '@_utils/customError/ApiError';
import QUERY_KEYS from '@_constants/queryKeys';
import { postDarakbangEntrance } from '@_apis/posts';

export default function useEnterDarakbang({
  onSuccess,
  onError,
}: {
  onSuccess?: () => void;
  onError: (string: string) => void;
}) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({ code, nickname }: { code: string; nickname: string }) =>
      postDarakbangEntrance({ code, nickname }),
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.myDarakbangs] });

      if (onSuccess) onSuccess();
    },

    onError: (err) => {
      if (err instanceof ApiError) {
        onError(err.message);
      }
      throw err;
    },
  });
}
