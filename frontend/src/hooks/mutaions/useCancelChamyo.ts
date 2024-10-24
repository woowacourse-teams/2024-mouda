import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';
import { deleteCancelChamyo } from '@_apis/deletes';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';

export default function useCancelChamyo() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: deleteCancelChamyo,
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: [
          QUERY_KEYS.darakbang,
          getLastDarakbangId(),
          QUERY_KEYS.chamyoMine,
        ],
      });
      queryClient.invalidateQueries({
        queryKey: [
          QUERY_KEYS.darakbang,
          getLastDarakbangId(),
          QUERY_KEYS.chamyoAll,
        ],
      });
      queryClient.invalidateQueries({
        queryKey: [QUERY_KEYS.darakbang, getLastDarakbangId(), QUERY_KEYS.moim],
      });
    },
  });
}
