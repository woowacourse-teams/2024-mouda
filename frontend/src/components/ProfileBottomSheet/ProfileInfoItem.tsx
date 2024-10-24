import { css, useTheme } from '@emotion/react';

interface InfoItemProps {
  label: string;
  value: string;
}

export default function InfoItem(props: InfoItemProps) {
  const { label, value } = props;

  const theme = useTheme();

  return (
    <div
      css={css`
        display: flex;
        flex-direction: column;
        gap: 0.4rem;
      `}
    >
      <p
        css={css`
          ${theme.typography.s2}
        `}
      >
        {label}
      </p>
      <p
        css={css`
          ${theme.typography.b1}
        `}
      >
        {value}
      </p>
    </div>
  );
}
