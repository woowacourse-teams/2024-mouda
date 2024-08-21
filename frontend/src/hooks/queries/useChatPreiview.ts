import QUERY_KEYS from '@_constants/queryKeys';
import { getChatPreview } from '@_apis/gets';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import { useQuery } from '@tanstack/react-query';

export default function useChatPreviews() {
  const { data: chatPreviews, isLoading } = useQuery({
    queryKey: [
      QUERY_KEYS.darakbang,
      getLastDarakbangId(),
      QUERY_KEYS.chatPreview,
    ],
    queryFn: getChatPreview,

    refetchInterval: 100,
  });

  return { chatPreviews, isLoading };
}
