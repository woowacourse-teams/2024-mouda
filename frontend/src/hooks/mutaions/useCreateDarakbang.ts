import { useMutation, useQueryClient } from '@tanstack/react-query';

import QUERY_KEYS from '@_constants/queryKeys';
import { postDarakbang } from '@_apis/posts';

export default function useCreateDarakbang(onSuccess?: (id: number) => void) {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: postDarakbang,
    onSuccess: (id: number) => {
      queryClient.invalidateQueries({ queryKey: [QUERY_KEYS.myDarakbangs] });

      if (onSuccess) onSuccess(id);
    },
  });
}
