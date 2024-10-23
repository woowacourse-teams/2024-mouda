import { css, useTheme } from '@emotion/react';
import { PropsWithChildren } from 'react';

interface BottomSheetProps {
  isOpen: boolean;
  onDimmerClick: () => void;

  header: React.ReactNode;
  cta?: React.ReactNode;

  size?: 'small' | 'medium' | 'large' | 'full';
}

export default function BottomSheet(
  props: PropsWithChildren<BottomSheetProps>,
) {
  const { isOpen, onDimmerClick, header, cta, children, size } = props;

  const theme = useTheme();

  if (!isOpen) {
    return null;
  }

  return (
    <BottomSheetContainer>
      <Dimmer onClick={onDimmerClick} />
      <div
        css={css`
          z-index: 2;

          display: flex;
          flex-direction: column;
          gap: 24px;

          width: 100%;
          max-width: 600px;
          height: ${size === 'medium'
            ? '50vh'
            : size === 'large'
              ? '80vh'
              : size === 'full'
                ? '100vh'
                : 'auto'};
          padding-bottom: 32px;

          background-color: ${theme.colorPalette.white[100]};
          border-radius: 28px 28px 0 0;
        `}
      >
        {header}
        {children}
        {cta}
      </div>
    </BottomSheetContainer>
  );
}

function BottomSheetContainer({ children }: { children: React.ReactNode }) {
  return (
    <div
      css={css`
        position: fixed;
        z-index: 1;
        inset: 0;

        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: flex-end;

        width: 100%;
        height: 100%;
      `}
    >
      {children}
    </div>
  );
}

function Dimmer({ onClick }: { onClick: () => void }) {
  return (
    <div
      onClick={onClick}
      css={css`
        position: fixed;
        inset: 0;

        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: flex-end;

        width: 100%;
        height: 100%;

        background-color: rgb(0 0 0 / 20%);
      `}
    />
  );
}

BottomSheet.Header = function BottomSheetHeader(props: PropsWithChildren) {
  const { children } = props;

  return <div css={{ padding: '32px 24px 0' }}>{children}</div>;
};

BottomSheet.Content = function BottomSheetContent(props: PropsWithChildren) {
  const { children } = props;

  return <div css={{ padding: '0px 24px' }}>{children}</div>;
};

BottomSheet.CTA = function BottomSheetCTA(props: PropsWithChildren) {
  const { children } = props;

  return <div css={{ padding: '0px 24px 16px' }}>{children}</div>;
};
