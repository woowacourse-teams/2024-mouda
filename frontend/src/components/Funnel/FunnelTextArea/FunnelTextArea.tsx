import { useTheme } from '@emotion/react';
import * as S from './FunnelTextArea.style';

interface FunnelTextAreaProps
  extends React.TextareaHTMLAttributes<HTMLTextAreaElement> {}

export default function FunnelTextArea(props: FunnelTextAreaProps) {
  const { ...rest } = props;

  const theme = useTheme();

  return <textarea css={S.textArea({ theme })} {...rest} />;
}
