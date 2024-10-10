import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { deleteMyInfo } from '@_apis/deletes';
import { removeAccessToken } from '@_utils/tokenManager';

export default function useDeleteMyInfo() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: deleteMyInfo,
    onSuccess: () => {
      removeAccessToken();
      queryClient.invalidateQueries({
        queryKey: [
          QUERY_KEYS.darakbang,
          getLastDarakbangId(),
          QUERY_KEYS.myInfo,
        ],
      });
    },
  });
}
