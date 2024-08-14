import * as S from './ErrorControlledInput.style';

import { HTMLProps } from 'react';
import { useTheme } from '@emotion/react';

interface ErrorControlledInputProps extends HTMLProps<HTMLInputElement> {
  errorMessage: string;
}

export default function ErrorControlledInput(props: ErrorControlledInputProps) {
  const { errorMessage, ...inputProps } = props;
  const theme = useTheme();

  return (
    <div css={S.container}>
      <input type="text" {...inputProps} css={S.input({ theme })} />
      {errorMessage && (
        <span css={S.errorMessage({ theme })}>{errorMessage}</span>
      )}
    </div>
  );
}
