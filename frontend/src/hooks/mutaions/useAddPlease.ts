import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { postPlease } from '@_apis/posts';

export default function useAddPlease(onSuccess: () => void) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: postPlease,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [
          QUERY_KEYS.darakbang,
          getLastDarakbangId(),
          QUERY_KEYS.please,
        ],
      });
      onSuccess();
    },
  });
}
