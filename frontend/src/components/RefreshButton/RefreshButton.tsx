import { useQueryClient } from '@tanstack/react-query';
import QUERY_KEYS from '@_constants/queryKeys';
import { getLastDarakbangId } from '@_common/lastDarakbangManager';
import Refresh from '@_common/assets/refresh.svg';
import { noneBaackgroundButton } from './RefreshButton.style';
export default function RefreshButton() {
  const queryClient = useQueryClient();
  return (
    <button
      css={noneBaackgroundButton}
      onClick={() =>
        queryClient.invalidateQueries({
          queryKey: [QUERY_KEYS.darakbang, getLastDarakbangId()],
        })
      }
      aria-label="새로고침"
    >
      <Refresh />
    </button>
  );
}
