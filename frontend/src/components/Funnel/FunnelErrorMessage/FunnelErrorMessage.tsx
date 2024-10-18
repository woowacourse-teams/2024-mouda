import { useTheme } from '@emotion/react';
import * as S from './FunnelErrorMessage.style';

interface FunnelErrorMessageProps {
  isError: boolean;
  errorMessage: string;
}

export default function FunnelErrorMessage(props: FunnelErrorMessageProps) {
  const { isError, errorMessage } = props;

  const theme = useTheme();

  if (!isError) {
    return null;
  }
  return <div css={S.errorMessage({ theme })}>{errorMessage}</div>;
}
