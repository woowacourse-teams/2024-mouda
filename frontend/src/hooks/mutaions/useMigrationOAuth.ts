import { kakaoOAuth } from '@_apis/auth';
import { useMutation } from '@tanstack/react-query';

export default function useMigrationOAuth(
  onSuccess: () => void,
  onError: () => void,
) {
  return useMutation({
    mutationFn: kakaoOAuth,
    onSuccess: onSuccess,
    onError: onError,
  });
}
