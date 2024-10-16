import QUERY_KEYS from '@_constants/queryKeys';
import { getChatRoomDetail } from '@_apis/gets';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { useQuery } from '@tanstack/react-query';

export default function useChatRoomDetail(chatRoomId: number) {
  const { data, isLoading } = useQuery({
    queryKey: [
      QUERY_KEYS.darakbang,
      getLastDarakbangId(),
      QUERY_KEYS.chatRoomDetail,
      chatRoomId,
    ],
    queryFn: () => getChatRoomDetail(chatRoomId),
  });

  return { chatRoomDetail: data, isLoading };
}
