import { getNotifications } from '@_apis/gets';
import QUERY_KEYS from '@_constants/queryKeys';
import { useQuery } from '@tanstack/react-query';

export default function useNotification() {
  const { data: notifications, isLoading } = useQuery({
    queryKey: [QUERY_KEYS.notifications],
    queryFn: getNotifications,
  });

  return { notifications, isLoading };
}
