import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { postConfirmDatetime } from '@_apis/posts';

export default function useConfirmDateTime() {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async ({
      chatRoomId,
      date,
      time,
    }: {
      chatRoomId: number;
      date: string;
      time: string;
    }) => postConfirmDatetime(chatRoomId, date, time),
    onSuccess: (chatRoomId: number | string) => {
      queryClient.invalidateQueries({
        queryKey: [
          QUERY_KEYS.darakbang,
          getLastDarakbangId(),
          QUERY_KEYS.chatRoomDetail,
          chatRoomId,
        ],
      });
    },
  });
}
