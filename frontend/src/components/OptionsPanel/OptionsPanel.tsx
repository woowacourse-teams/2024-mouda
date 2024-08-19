export interface OptionsPanelOption {
  description: string;
  onClick: () => void;
  hasTopBorder?: boolean;
}

interface OptionsPanelProps {
  options: OptionsPanelOption[];
  onClose: () => void;
  onAfterSelect: () => void;
  movedHeight?: string;
  movedWidth?: string;
  width?: string;
}

import * as S from './OptionsPanel.style';

import { useEffect } from 'react';
import { useTheme } from '@emotion/react';

export default function OptionsPanel(props: OptionsPanelProps) {
  const { options, onClose, onAfterSelect, width, movedHeight, movedWidth } =
    props;
  const theme = useTheme();

  useEffect(() => {
    document.addEventListener('click', onClose);
    return () => document.removeEventListener('click', onClose);
  }, [onClose]);

  return (
    <div
      css={S.panel({
        width: width || '100px',
        movedHeight: movedHeight || '0px',
        movedWidth: movedWidth || '0px',
      })}
    >
      {options.map(({ description, onClick, hasTopBorder }) => {
        return (
          <div
            key={description}
            onClick={(e) => {
              e.stopPropagation();
              onClick();
              onAfterSelect();
            }}
            css={S.option({ theme, hasTopBorder })}
          >
            <span css={theme.typography.b1}>{description}</span>
          </div>
        );
      })}
    </div>
  );
}
