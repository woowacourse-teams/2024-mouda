import type { Meta, StoryObj } from '@storybook/react';

import OptionsPanel from './OptionsPanel';
import { useState } from 'react';

const meta: Meta<typeof OptionsPanel> = {
  component: OptionsPanel,
};

export default meta;
type Story = StoryObj<typeof OptionsPanel>;

const DummyElement = (
  <div style={{ backgroundColor: 'red' }}>
    {' '}
    국군의 조직과 편성은 법률로 정한다. 법원은 최고법원인 대법원과 각급법원으로
    조직된다. 저작자·발명가·과학기술자와 예술가의 권리는 법률로써 보호한다.
    지방의회의 조직·권한·의원선거와 지방자치단체의 장의 선임방법 기타
    지방자치단체의 조직과 운영에 관한 사항은 법률로 정한다. 제1항의 해임건의는
    국회재적의원 3분의 1 이상의 발의에 의하여 국회재적의원 과반수의 찬성이
    있어야 한다. 근로조건의 기준은 인간의 존엄성을 보장하도록 법률로 정한다.
    타인의 범죄행위로 인하여 생명·신체에 대한 피해를 받은 국민은 법률이 정하는
    바에 의하여 국가로부터 구조를 받을 수 있다. 국가는 건전한 소비행위를
    계도하고 생산품의 품질향상을 촉구하기 위한 소비자보호운동을 법률이 정하는
    바에 의하여 보장한다. 국회는 의원의 자격을 심사하며, 의원을 징계할 수 있다.
    외국인은 국제법과 조약이 정하는 바에 의하여 그 지위가 보장된다. 국가는
    평생교육을 진흥하여야 한다. 훈장등의 영전은 이를 받은 자에게만 효력이 있고,
    어떠한 특권도 이에 따르지 아니한다. 국민의 자유와 권리는 헌법에 열거되지
    아니한 이유로 경시되지 아니한다. 비상계엄하의 군사재판은 군인·군무원의
    범죄나 군사에 관한 간첩죄의 경우와 초병·초소·유독음식물공급·포로에 관한 죄중
    법률이 정한 경우에 한하여 단심으로 할 수 있다. 다만, 사형을 선고한 경우에는
    그러하지 아니하다. 모든 국민은 헌법과 법률이 정한 법관에 의하여 법률에 의한
    재판을 받을 권리를 가진다. 대통령은 법률이 정하는 바에 의하여 사면·감형 또는
    복권을 명할 수 있다.
  </div>
);

export const Default: Story = {
  args: {
    options: [
      {
        description: 'hellodddddddddddddddddddddddddddddd',
        onClick: () => {
          alert('hello');
        },
      },
      {
        description:
          'helloddddddddddddddddddddddddddddddddddddddddddddddddddddddd',
        onClick: () => {
          alert('hello');
        },
      },
      {
        description: '안녕',
        onClick: () => {
          alert('안녕');
        },
        hasTopBorder: true,
      },
    ],
    width: '200px',
    onClose: () => alert('close'),
  },
};
export const Decorated: Story = {
  args: {
    options: [
      {
        description: 'hellodddddddddddddddddddddddddddddddddddddddddd',
        onClick: () => {
          alert('hello');
        },
      },
    ],
    width: '200px',
    movedHeight: '24px',
  },
  decorators: function (Story) {
    const [isStoryOpen, setIsStoryOpen] = useState(false);
    return (
      <div>
        {DummyElement}
        <header style={{ display: 'flex', justifyContent: 'space-evenly' }}>
          <button onClick={() => setIsStoryOpen(!isStoryOpen)}>
            클릭시 오픈 토글
          </button>
          {isStoryOpen && (
            <Story
              onClose={() => {
                setIsStoryOpen(false);
                alert('close');
              }}
            />
          )}
        </header>
        {DummyElement}
      </div>
    );
  },
};
