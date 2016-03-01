function callback( mu, pc )

    if strcmp(pc.get('PropertyName'), 'zoom')
        fprintf('Zoom is %f\n', pc.get('newValue'));
    end
    

end

