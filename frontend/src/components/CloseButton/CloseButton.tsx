import CloseIcon from '@_components/Icons/CloseIcon';
import { css } from '@emotion/react';
import { ButtonHTMLAttributes } from 'react';

interface CloseButtonProps extends ButtonHTMLAttributes<HTMLButtonElement> {}

export default function CloseButton(props: CloseButtonProps) {
  const { ...rest } = props;

  return (
    <button
      css={css`
        padding: 0.4rem;
        background: none;
        border: none;
      `}
      {...rest}
    >
      <CloseIcon />
    </button>
  );
}
