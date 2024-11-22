import { useTheme } from '@emotion/react';
import * as S from './FunnelRadioCardGroupOption.style';
import SelectionIcon from '@_components/Icons/SelectionIcon';

interface FunnelRadioCardGroupOptionProps {
  title: string;
  description?: string;
  isSelected: boolean;
  onSelect: () => void;
}

export default function FunnelRadioCardGroupOption(
  props: FunnelRadioCardGroupOptionProps,
) {
  const { title, description, isSelected, onSelect } = props;

  const theme = useTheme();

  return (
    <div
      css={S.container({ theme, isSelected, description })}
      onClick={onSelect}
    >
      <div css={S.contentContainer()}>
        <h2 css={theme.typography.s1}>{title}</h2>
        {description && <p css={theme.typography.s2}>{description}</p>}
      </div>
      <div css={S.selectionContainer}>
        <SelectionIcon isSelected={isSelected} />
      </div>
    </div>
  );
}
