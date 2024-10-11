import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { postConfirmPlace } from '@_apis/posts';

export default function useConfirmPlace() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: ({
      chatRoomId,
      place,
    }: {
      chatRoomId: number;
      place: string;
    }) => postConfirmPlace(chatRoomId, place),
    onSuccess: (chatRoomId: number | string) => {
      queryClient.invalidateQueries({
        queryKey: [
          QUERY_KEYS.darakbang,
          getLastDarakbangId(),
          QUERY_KEYS.moim,
          chatRoomId,
        ],
      });
    },
  });
}
